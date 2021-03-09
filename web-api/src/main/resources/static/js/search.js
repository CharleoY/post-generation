$(document).ready(function() {
    $(document).on('click', '.tw-block-parent', function() {

        let checked = $(this).find('input[type="checkbox"]').checked;
        console.log("checkbox is " + checked)

        if (typeof checked === 'undefined' ) {
            checked = true;
            console.log("typeof " + checked)
        } else {
            console.log("non typeof")
            checked = !checked;
        }

        $(this).find('input[type="checkbox"]').checked = checked
        console.log('.tw-block-parent clicked')
    });
});

getCheckedList = function() {
    var selected = [];
    $('#checkboxes input:checked').each(function() {
        selected.push($(this).attr('id'));
    });

    console.log(selected)
    return selected
};

