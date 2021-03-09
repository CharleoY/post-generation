# Copyright (c) 2019, NVIDIA CORPORATION. All rights reserved.
#
# This work is licensed under the Creative Commons Attribution-NonCommercial
# 4.0 International License. To view a copy of this license, visit
# http://creativecommons.org/licenses/by-nc/4.0/ or send a letter to
# Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.

"""Minimal script for reproducing the figures of the StyleGAN paper using pre-trained generators."""


import os
import pickle
import numpy as np
import PIL.Image
import dnnlib
import dnnlib.tflib as tflib
import config
# import cv2
#----------------------------------------------------------------------------
# Helpers for loading and using pre-trained generators.

url_ffhq        = 'https://drive.google.com/uc?id=1MEGjdvVpUsu1jB4zrXZN7Y4kBBOzizDQ' # karras2019stylegan-ffhq-1024x1024.pkl
url_celebahq    = 'https://drive.google.com/uc?id=1MGqJl28pN4t7SAtSrPdSRJSQJqahkzUf' # karras2019stylegan-celebahq-1024x1024.pkl
url_bedrooms    = 'https://drive.google.com/uc?id=1MOSKeGF0FJcivpBI7s63V9YHloUTORiF' # karras2019stylegan-bedrooms-256x256.pkl
url_cars        = 'https://drive.google.com/uc?id=1MJ6iCfNtMIRicihwRorsM3b7mmtmK9c3' # karras2019stylegan-cars-512x384.pkl
url_cats        = 'https://drive.google.com/uc?id=1MQywl0FNt6lHu8E_EUqnRbviagS7fbiJ' # karras2019stylegan-cats-256x256.pkl

synthesis_kwargs = dict(output_transform=dict(func=tflib.convert_images_to_uint8, nchw_to_nhwc=True), minibatch_size=8)

_Gs_cache = dict()

def load_Gs(url):
    if url not in _Gs_cache:
        with open('./LSUN-Stanford-car-dataset-network-snapshot.pkl','rb') as f:
            _G, _D, Gs = pickle.load(f)
        _Gs_cache[url] = Gs
    return _Gs_cache[url]

#----------------------------------------------------------------------------
# Figures 2, 3, 10, 11, 12: Multi-resolution grid of uncurated result images.


# def dodgeNaive(image, mask):
#     # determine the shape of the input image
#     width, height = image.shape[:2]
#
#     # prepare output argument with same size as image
#     blend = np.zeros((width, height), np.uint8)
#
#     for col in range(width):
#         for row in range(height):
#             # do for every pixel
#             if mask[col, row] == 255:
#                 # avoid division by zero
#                 blend[col, row] = 255
#             else:
#                 # shift image pixel value by 8 bits
#                 # divide by the inverse of the mask
#                 tmp = (image[col, row] << 8) / (255 - mask)
#                 # print('tmp={}'.format(tmp.shape))
#                 # make sure resulting value stays within bounds
#                 if tmp.any() > 255:
#                     tmp = 255
#                     blend[col, row] = tmp
#
#     return blend

#
# def dodgeV2(image, mask):
#     return cv2.divide(image, 255 - mask, scale=256)
#
#
# def burnV2(image, mask):
#     return 255 - cv2.divide(255 - image, 255 - mask, scale=256)
#
#
# def rgb_to_sketch(src_image_name, dst_image_name):
#     img_rgb = cv2.imread(src_image_name)
#     img_gray = cv2.cvtColor(img_rgb, cv2.COLOR_BGR2GRAY)
#     # 读取图片时直接转换操作
#     # img_gray = cv2.imread('example.jpg', cv2.IMREAD_GRAYSCALE)
#
#     img_gray_inv = 255 - img_gray
#     img_blur = cv2.GaussianBlur(img_gray_inv, ksize=(21, 21),
#                                 sigmaX=0, sigmaY=0)
#     img_blend = dodgeV2(img_gray, img_blur)
#
#     # cv2.imshow('original', img_rgb)
#     # cv2.imshow('gray', img_gray)
#     # cv2.imshow('gray_inv', img_gray_inv)
#     # cv2.imshow('gray_blur', img_blur)
#     # cv2.imshow("pencil sketch", img_blend)
#     # cv2.waitKey(0)
#     # cv2.destroyAllWindows()
#     cv2.imwrite(dst_image_name, img_blend)


def draw_uncurated_result_figure(png, Gs, cx, cy, cw, ch, rows, lods, seed):
    print(png)
    latents = np.random.RandomState(seed).randn(sum(rows * 2**lod for lod in lods), Gs.input_shape[1])
    images = Gs.run(latents, None, **synthesis_kwargs) # [seed, y, x, rgb]

    canvas = PIL.Image.new('RGB', (sum(cw // 2**lod for lod in lods), ch * rows), 'white')
    image_iter = iter(list(images))
    for col, lod in enumerate(lods):
        for row in range(rows * 2**lod):
            image = PIL.Image.fromarray(next(image_iter), 'RGB')
            image = image.crop((cx, cy, cx + cw, cy + ch))
            image = image.resize((cw // 2**lod, ch // 2**lod), PIL.Image.ANTIALIAS)
            canvas.paste(image, (sum(cw // 2**lod for lod in lods[:col]), row * ch // 2**lod))
    canvas.save(png)

#----------------------------------------------------------------------------
# Figure 3: Style mixing.

def draw_style_mixing_figure(png, Gs, w, h, src_seeds, dst_seeds, style_ranges):
    print(png)
    src_latents = np.stack(np.random.RandomState(seed).randn(Gs.input_shape[1]) for seed in src_seeds)
    dst_latents = np.stack(np.random.RandomState(seed).randn(Gs.input_shape[1]) for seed in dst_seeds)

    print(src_latents.shape)
    print('_________________________________')
    src_dlatents = Gs.components.mapping.run(src_latents, None) # [seed, layer, component]
    dst_dlatents = Gs.components.mapping.run(dst_latents, None) # [seed, layer, component]
    print(src_dlatents.shape)
    src_images = Gs.components.synthesis.run(src_dlatents, randomize_noise=False, **synthesis_kwargs)
    print(src_dlatents.shape)
    dst_images = Gs.components.synthesis.run(dst_dlatents, randomize_noise=False, **synthesis_kwargs)

    canvas = PIL.Image.new('RGB', (w * (len(src_seeds) + 1), h * (len(dst_seeds) + 1)), 'white')
    for col, src_image in enumerate(list(src_images)):
        canvas.paste(PIL.Image.fromarray(src_image, 'RGB'), ((col + 1) * w, 0))
    for row, dst_image in enumerate(list(dst_images)):
        canvas.paste(PIL.Image.fromarray(dst_image, 'RGB'), (0, (row + 1) * h))
        row_dlatents = np.stack([dst_dlatents[row]] * len(src_seeds))
        row_dlatents[:, style_ranges[row]] = src_dlatents[:, style_ranges[row]]
        row_images = Gs.components.synthesis.run(row_dlatents, randomize_noise=False, **synthesis_kwargs)
        for col, image in enumerate(list(row_images)):
            canvas.paste(PIL.Image.fromarray(image, 'RGB'), ((col + 1) * w, (row + 1) * h))
    print(row_dlatents.shape)

    canvas.save(png)



from tqdm.auto import tqdm
def generate(Gs,src_seeds):
    src_latents = np.stack(np.random.RandomState(seed).randn(Gs.input_shape[1]) for seed in src_seeds)
    for i,item in enumerate(tqdm(src_latents)):
        a = np.expand_dims(item, axis=0)
        new_dlatent = Gs.components.mapping.run(a, None)
        new_image = Gs.components.synthesis.run(new_dlatent, randomize_noise=False, psi=1 ,**synthesis_kwargs)
        canvas = PIL.Image.new('RGB', (512, 384), 'white')
        canvas.paste(PIL.Image.fromarray(new_image[0], 'RGB'))

        print(a.shape)
        print(new_dlatent.shape)
        canvas.save('./generated_images/{}.jpg'.format(str(i)))
        np.save('./latent_representations/{}.npy'.format(str(i)),new_dlatent)


def draw_style_mixing_figure2(png, Gs, w, h, src_seeds, dst_seeds, style_ranges):

    src_latents = np.stack(np.random.RandomState(seed).randn(Gs.input_shape[1]) for seed in src_seeds)
    dst_latents = np.stack(np.random.RandomState(seed).randn(Gs.input_shape[1]) for seed in dst_seeds)

    for i,s in enumerate(src_latents):
        for j,d in enumerate(dst_latents):
            a = np.expand_dims(s, axis=0)
            b = np.expand_dims(d, axis=0)
            new= 0.5*(a+b)
            new_dlatent = Gs.components.mapping.run(new, None)
            print(new_dlatent.shape)
            new_image = Gs.components.synthesis.run(new_dlatent, randomize_noise=False, **synthesis_kwargs)
            print(new_image.shape)
            canvas = PIL.Image.new('RGB',(512,384), 'white')
            canvas.paste(PIL.Image.fromarray(new_image[0], 'RGB'))
            canvas.save('./{}_{}.png'.format(str(i),str(j)))
            # rgb_to_sketch('./{}_{}.png'.format(str(i),str(j)),'./{}_{}post.png'.format(str(i),str(j)))


#----------------------------------------------------------------------------
# Figure 4: Noise detail.

def draw_noise_detail_figure(png, Gs, w, h, num_samples, seeds):
    print(png)
    canvas = PIL.Image.new('RGB', (w * 3, h * len(seeds)), 'white')
    for row, seed in enumerate(seeds):
        latents = np.stack([np.random.RandomState(seed).randn(Gs.input_shape[1])] * num_samples)
        images = Gs.run(latents, None, truncation_psi=1, **synthesis_kwargs)
        canvas.paste(PIL.Image.fromarray(images[0], 'RGB'), (0, row * h))
        for i in range(4):
            crop = PIL.Image.fromarray(images[i + 1], 'RGB')
            crop = crop.crop((650, 180, 906, 436))
            crop = crop.resize((w//2, h//2), PIL.Image.NEAREST)
            canvas.paste(crop, (w + (i%2) * w//2, row * h + (i//2) * h//2))
        diff = np.std(np.mean(images, axis=3), axis=0) * 4
        diff = np.clip(diff + 0.5, 0, 255).astype(np.uint8)
        canvas.paste(PIL.Image.fromarray(diff, 'L'), (w * 2, row * h))
    canvas.save(png)

#----------------------------------------------------------------------------
# Figure 5: Noise components.

def draw_noise_components_figure(png, Gs, w, h, seeds, noise_ranges, flips):
    print(png)
    Gsc = Gs.clone()
    noise_vars = [var for name, var in Gsc.components.synthesis.vars.items() if name.startswith('noise')]
    noise_pairs = list(zip(noise_vars, tflib.run(noise_vars))) # [(var, val), ...]
    latents = np.stack(np.random.RandomState(seed).randn(Gs.input_shape[1]) for seed in seeds)
    all_images = []
    for noise_range in noise_ranges:
        tflib.set_vars({var: val * (1 if i in noise_range else 0) for i, (var, val) in enumerate(noise_pairs)})
        range_images = Gsc.run(latents, None, truncation_psi=1, randomize_noise=False, **synthesis_kwargs)
        range_images[flips, :, :] = range_images[flips, :, ::-1]
        all_images.append(list(range_images))

    canvas = PIL.Image.new('RGB', (w * 2, h * 2), 'white')
    for col, col_images in enumerate(zip(*all_images)):
        canvas.paste(PIL.Image.fromarray(col_images[0], 'RGB').crop((0, 0, w//2, h)), (col * w, 0))
        canvas.paste(PIL.Image.fromarray(col_images[1], 'RGB').crop((w//2, 0, w, h)), (col * w + w//2, 0))
        canvas.paste(PIL.Image.fromarray(col_images[2], 'RGB').crop((0, 0, w//2, h)), (col * w, h))
        canvas.paste(PIL.Image.fromarray(col_images[3], 'RGB').crop((w//2, 0, w, h)), (col * w + w//2, h))
    canvas.save(png)

#----------------------------------------------------------------------------
# Figure 8: Truncation trick.

def draw_truncation_trick_figure(png, Gs, w, h, seeds, psis):
    print(png)
    latents = np.stack(np.random.RandomState(seed).randn(Gs.input_shape[1]) for seed in seeds)
    dlatents = Gs.components.mapping.run(latents, None) # [seed, layer, component]
    dlatent_avg = Gs.get_var('dlatent_avg') # [component]

    canvas = PIL.Image.new('RGB', (w * len(psis), h * len(seeds)), 'white')
    for row, dlatent in enumerate(list(dlatents)):
        row_dlatents = (dlatent[np.newaxis] - dlatent_avg) * np.reshape(psis, [-1, 1, 1])
        row_images = Gs.components.synthesis.run(row_dlatents, randomize_noise=False, **synthesis_kwargs)

        for col, image in enumerate(list(row_images)):
            canvas.paste(PIL.Image.fromarray(image, 'RGB'), (col * w, row * h))
    canvas.save(png)

def mix(dlatent1,dlatent2,Gs):

    d1 = np.load(dlatent1)
    d2 = np.load(dlatent2)
    savg = 0.5 * (d1 + d2)
    synthesis_kwargs = dict(output_transform=dict(func=tflib.convert_images_to_uint8, nchw_to_nhwc=False),
                            minibatch_size=8)
    row_images = Gs.components.synthesis.run(savg, randomize_noise=False, **synthesis_kwargs)
    print(row_images.shape)
    images = PIL.Image.fromarray(row_images.transpose((0, 2, 3, 1))[0], 'RGB').resize((512, 512), PIL.Image.LANCZOS)
    images.show()

#----------------------------------------------------------------------------
# Main program.
# def generate(Gs,src_seeds):
#     src_latents = np.stack(np.random.RandomState(seed).randn(Gs.input_shape[1]) for seed in src_seeds)
#     for i,item in enumerate(tqdm(src_latents)):
#         a = np.expand_dims(item, axis=0)
#         new_dlatent = Gs.components.mapping.run(a, None)
#         new_image = Gs.components.synthesis.run(new_dlatent, randomize_noise=False, **synthesis_kwargs)
#         canvas = PIL.Image.new('RGB', (512, 384), 'white')
#         canvas.paste(PIL.Image.fromarray(new_image[0], 'RGB'))
#         canvas.save('./generated_images/{}.jpg'.format(str(i)))
#         np.save('./latent_representations/{}.npy'.format(str(i)),new_dlatent)
import matplotlib.pyplot as plt
def generate_image(Gs,latent_vector):
    latent_vector = latent_vector.reshape((1, 16, 512))
    # new_dlatent = Gs.components.mapping.run(latent_vector, None)
    new_image = Gs.components.synthesis.run(latent_vector, randomize_noise=False, **synthesis_kwargs)
    img = PIL.Image.fromarray(new_image[0], 'RGB')
    return img

def move_and_show(Gs,latent_vectors, direction, coeffs,k):
    fig,ax = plt.subplots(len(latent_vectors), len(coeffs), figsize=(7, 7), dpi=300)
    for j,latent_vector in enumerate(latent_vectors):
        for i, coeff in enumerate(coeffs):
            new_latent_vector = latent_vector.copy()
            new_latent_vector[:12] = (latent_vector + coeff*direction)[:12]
            ax[j][i].imshow(generate_image(Gs,new_latent_vector))
            # ax[j][i].set_title('%d' % i)
            ax[j][i].axis('off')
    plt.savefig('G:/Projects/gan/results/d{}.jpg'.format(str(k)))
    plt.close()

import random
def main():
    # r=np.random.randint(18,size=30)
    # X_data=np.load('d:/data.npy')
    # coef = np.load('./coef.npy')
    # print(X_data.reshape((-1, 16, 512))[0].reshape((1, 16, 512)).shape)


    tflib.init_tf()
    os.makedirs(config.result_dir, exist_ok=True)
    #
    # for i in range(9):
    #     for j in range(9):
    # for i in tqdm(range(17)):
    #     if i == 4 or i==5 or i ==12 or i==13 or i ==9:
    #         continue
    #     move_and_show(load_Gs('url_ffhq'),X_data.reshape((-1, 16, 512))[119:126], coef[i], [-2.5,-1, 0,1,2.5, 5,6],i)



    # draw_uncurated_result_figure(os.path.join(config.result_dir, 'figure02-uncurated-ffhq.png'), load_Gs(url_ffhq), cx=0, cy=0, cw=512, ch=384, rows=3, lods=[0,1,2,2,3,3], seed=99)
    # draw_style_mixing_figure(os.path.join(config.result_dir, 'figure03-style-mixing.png'),
    #           load_Gs(url_cars), w=512, h=384,
    #           src_seeds=[1,9,3,5,7], dst_seeds=[144,244,6,3444],
    #           style_ranges=[range(0,4)]*3+[range(4,8)]*2+[range(8,18)])
    # j = 0
    # # x = [0] * 5
    # while j < 16:
    #
    #
    #     x = [j]*5
    #     draw_style_mixing_figure(os.path.join(config.result_dir, '{}.png'.format(str(j))), load_Gs(url_cars), w=512,
    #                              h=384, src_seeds=[1, 9, 3, 5,10], dst_seeds=[144, 244, 6, 3444,11],
    #                              style_ranges=x)
    #     j += 1
    # x = [4,4,4,0]
    # x.extend([1]*26)
    # draw_style_mixing_figure2('', load_Gs(url_cars), w=512,
    #                          h=384, src_seeds=[1, 9], dst_seeds=[144, 244],
    #                          style_ranges='')
    generate(Gs=load_Gs(url_cars),src_seeds=[random.randint(1,9999)  for x in range(10)])

    # draw_noise_detail_figure(os.path.join(config.result_dir, 'figure04-noise-detail.png'), load_Gs(url_ffhq), w=512, h=384, num_samples=100, seeds=[1153,152,111,222,333])
    # draw_noise_components_figure(os.path.join(config.result_dir, 'figure05-noise-components.png'), load_Gs(url_ffhq), w=512, h=384, seeds=[1917,1155], noise_ranges=[range(0, 18), range(0, 0), range(8, 18), range(0, 8)], flips=[1])
    # draw_truncation_trick_figure(os.path.join(config.result_dir, 'figure08-truncation-trick.png'), load_Gs(url_ffhq), w=512, h=384, seeds=[144,244,3994,3444,5555], psis=[1, 0.7, 0.5, 0, -0.5,-0.7, -1])
    # draw_uncurated_result_figure(os.path.join(config.result_dir, 'figure10-uncurated-bedrooms.png'), load_Gs(url_bedrooms), cx=0, cy=0, cw=256, ch=256, rows=5, lods=[0,0,1,1,2,2,2], seed=0)
    # draw_uncurated_result_figure(os.path.join(config.result_dir, 'figure11-uncurated-cars.png'), load_Gs(url_cars), cx=0, cy=64, cw=512, ch=384, rows=4, lods=[0,1,2,2,3,3], seed=2)
    # draw_uncurated_result_figure(os.path.join(config.result_dir, 'figure12-uncurated-cats.png'), load_Gs(url_cats), cx=0, cy=0, cw=256, ch=256, rows=5, lods=[0,0,1,1,2,2,2], seed=1)
    # mix(dlatent1='./latent_representations/0.npy',dlatent2='./latent_representations/1.npy',Gs=load_Gs(url_cars))
#----------------------------------------------------------------------------

if __name__ == "__main__":
    main()

