app.controller('loginCtrl', function ($scope, $http) {

    $scope.title = "Post generation";
    $scope.url = "http://34.123.186.188:8080/register";
    $scope.authError = false;
    $scope.ErrorMsg = "";

    // login btn
    $scope.LoginBtn = function (login, password) {
        $scope.ErrorMsg = "";
        $scope.P = "";
        let auth = {
            username: login,
            password: password
        };
        $http({
            url: $scope.url + "/auth/login",
            dataType: 'json',
            method: 'POST',
            data: auth,
            headers: {
                'Content-type': 'application/json',
            }
        }).then(function (response) {

            $scope.authError = false;
            $scope.dataAuth = response.data;
            $scope.token = $scope.dataAuth["token"];
            $scope.fullname = $scope.dataAuth["fullname"];
            $scope.role = $scope.dataAuth["role"];

            Cookies.set("token", $scope.token);
            Cookies.set("fullname", $scope.fullname);
            Cookies.set("role", $scope.role);

            window.location = $scope.url + "/search";

        }, function (response) {
            $scope.authError = true;
            let data = response.data;
            $scope.ErrorMsg = data["message"];
        })
    };

    $scope.LogoutBtn = function () {
        $scope.Token = '';
        $scope.UserName = '';
        $scope.UserPassword = '';
        $scope.bodyContent = 'pages/login.html';
    };

    $scope.switchToRegister = function () {
        $scope.title = "Registration";
        $scope.bodyContent = "../../templates/register.html"
    }
    $scope.switchToLogin = function () {
        $scope.title = "Login";
        $scope.bodyContent = "../../templates/login.html"
    }
    $scope.switchToHome = function () {
        $scope.title = "Home page";
        $scope.bodyContent = "../../templates/home.html"
    }
});

//Register Controller
app.controller('registerCtrl', function ($scope, $http) {

    $scope.title = "Post generation";
    $scope.url = "http://34.123.186.188:8080";
    $scope.authError = false;
    $scope.ErrorMsg = "";

    $scope.registerBtn = function (email, username, password, reEnterPassword) {
        $scope.ErrorMsg = "";
        $scope.P = "";
        let register = {
            email: email,
            username: username,
            password: password,
            reEnterPassword: reEnterPassword
        };

        $http({
            url: $scope.url + "/register",
            dataType: 'json',
            method: 'POST',
            data: register,
            headers: {
                'Content-type': 'application/json',
            }
        }).then(function (response) {
            $scope.authError = false;
            $scope.dataAuth = response.data;
            $scope.token = $scope.dataAuth["token"]
            $scope.fullname = $scope.dataAuth["fullname"]
            $scope.role = $scope.dataAuth["role"]

            Cookies.set("token", $scope.token);
            Cookies.set("fullname", $scope.fullname);
            Cookies.set("role", $scope.role);

            window.location = $scope.url + "/search";

        }, function (response) {
            $scope.authError = true;
            let data = response.data;
            $scope.ErrorMsg = data["message"];

        })
    }
});

//Index controller
app.controller('indexCtrl', function ($scope, $http) {

    $scope.title = "Post generation";
    $scope.url = "http://34.123.186.188:8080";
    $scope.authError = false;
    $scope.ErrorMsg = "";

});


//Search Controller
app.controller('searchCtrl', function ($scope, $http) {

    $scope.title = "Search posts with username";
    $scope.url = "http://34.123.186.188:8080";
    $scope.authError = false;
    $scope.ErrorMsg = "";
    $scope.Properties = new Properties()

    $scope.findUserId = function (username) {

    };

    $scope.findPostsByUserId = function (username) {
        $scope.authError = false;
        $scope.isSign();
        $http({
            url: $scope.url + "/twitter/username/" + username,
            dataType: 'json',
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': $scope.token
            }
        }).then(function (response) {

            let responseData = response.data;
            $scope.userId = responseData["id"];
            $scope.name = responseData["name"]
            $scope.username = responseData["username"]

            $http({
                url: $scope.url + "/twitter/find/tweet/byUsername/" + $scope.userId,
                dataType: 'json',
                method: 'GET',
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': $scope.token
                }
            }).then(function (byteResponse) {
                $scope.Tweets = $scope.parseResponse(byteResponse.data)
                console.log($scope.Tweets)

            }, function (response) {
                console.log(response.data)
            });

        }, function (response) {
            $scope.authError = true;
            $scope.ErrorMsg = response.data["message"];
        });

    }

    $scope.loadNextPage = function () {
        $http({
            url: $scope.url + "/twitter/" + $scope.userId + "/findByPaginationToken/" + $scope.nextToken,
            dataType: 'json',
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': $scope.token
            }
        }).then(function (response) {
                $scope.Tweets.push.apply($scope.Tweets, $scope.parseResponse(response.data));

            },
            function (errorResponse) {
            });
    }

    $scope.detectPersonality = function () {

        let json = "\"username\": \"" +$scope.username + "\"}"
        $http({
            url: "http://localhost:80/predict",
            dataType: 'json',
            method: 'POST',
            data: json,
            headers: {
                'Content-Type': 'application/json',
            }
        }).then(function (response) {

            $scope.Prediction = response.data["prediction"]

        },
        function (errorResponse) {
            });
    }

    $scope.sendRequestToGenerateText = function () {
        var texts = []
        var images = []
        let length = $scope.Tweets.length;
        $scope.detectPersonality()

        for( let i = 0; i < length; i ++) {
            let tweet = $scope.Tweets[i];

            if (tweet.selected === true) {
                texts.push(tweet.text)

                let urls = tweet.entities.urls;
                let urls_length = urls.length;

                for(let j = 0; j < urls_length; j ++) {
                    let url = urls[j];

                    for(let k = 0; k < url.images.length; k ++) {
                        let image = url.images[k];
                        images.push(image["url"])
                    }
                }
            }
        }

        $http({
            url: $scope.url + "/open-ai/get-image",
            dataType: 'json',
            method: 'GET',
            headers: {
                'Content-type': 'application/json',
                'Authorization': $scope.token
            }
        }).then(function (response) {
            console.log(response)
            $scope.generatedImageUrl = $scope.url + "/img/" + response.data.url;

        }, function (errorResponse) {

            console.log(errorResponse)
        });

        $http({
            url: $scope.url + "/open-ai/generate",
            dataType: 'json',
            method: 'POST',
            data: texts,
            headers: {
                'Content-type': 'application/json',
                'Authorization': $scope.token
            }
        }).then(function (response) {
            console.log(response)

            $scope.authError = false;
            $scope.generatedResponse = response.data;

            console.log($scope.generatedResponse)
            $scope.choices = $scope.generatedResponse["choices"]
            $scope.choice = $scope.choices[0]
            $scope.generatedText = $scope.choice["text"]

            console.log($scope.generatedText)
            $scope.generatedTweet = new GeneratedTweet($scope.generatedText, "", $scope.username, $scope.name)

        }, function (response) {
            $scope.authError = true;
            let data = response.data;
            $scope.ErrorMsg = data["message"];

        })

    }

    $scope.isSign = function () {
        if (Cookies.get("token") == null) {
            window.location = $scope.url + "/login";
        }

        $scope.token = Cookies.get("token");
        $scope.fullname = Cookies.get("fullname");
        $scope.role = Cookies.get("role");
    };

    $scope.parseResponse = function (response) {
        console.log(response)
        $scope.nextToken = response["meta"]["next_token"]

        let data = response["data"]
        let size = data.length

        var result = []

        for (let i = 0; i < size; i++) {
            let item = data[i];

            let id = item["id"]
            let text = item["text"]
            let authorId = item["author_id"]
            let conversationId = item["conversation_id"]

            var hashtags = [];
            var urls = [];
            if (item["entities"]) {

                let responseEntities = item["entities"]

                if (responseEntities["hashtags"]) {
                    let responseHashTag = responseEntities["hashtags"]

                    for (let j = 0; j < responseHashTag.length; j++) {

                        hashtags[j] = new HashTags(responseHashTag[j]['tag'])
                    }
                }

                if (responseEntities["urls"]) {

                    let responseUrls = responseEntities["urls"]
                    for( let k = 0; k < responseUrls.length; k ++ ) {

                        let responseUrl = responseUrls[k];
                        if (responseUrl["images"]) {

                            let responseImages = responseUrl["images"]
                            let imageCount = responseImages.length;
                            var images = []

                            for (let l = 0; l < imageCount; l++) {
                                if (l % 2 !== 1) {

                                    let image = responseImages[l];
                                    images[l] = new Images(image["url"], image["width"], image["height"])
                                }
                            }
                            urls[k] = new Urls(images, responseUrl["title"], responseUrl["description"])
                        }
                    }
                }
            }
            let entities = new Entities(hashtags, urls)
            result[i] = new Tweet(authorId, entities, text, id, conversationId, $scope.username, $scope.name)
        }


        return result
    }

    function Tweet(authorId, entities, text, id, conversationId, username, name) {
        this.id = id
        this.name = name
        this.text = text
        this.authorId = authorId
        this.entities = entities
        this.username = username
        this.conversationId = conversationId
        this.selected = false;

        return this
    }

    function Entities(hashtags, urls) {
        this.hashtags = hashtags
        this.urls = urls

        return this
    }

    function HashTags(tag) {
        this.tag = tag

        return this
    }

    function Urls(images, title, description) {
        this.images = images
        this.title = title
        this.description = description

        return this
    }

    function Images(url, width, height) {
        this.url = url
        this.width = width
        this.height = height

        return this;
    }

    function GeneratedTweet(text, url, name, username) {
        this.text = text
        this.url = url
    }

    function Properties() {
        this.temperature = 0
        this.maxTokens = 30
    }
});

