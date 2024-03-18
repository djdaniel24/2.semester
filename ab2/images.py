import numpy as np
import scipy.ndimage
from PIL import Image

import images

import utils

def read_img(inp:str) -> Image.Image:
    """
        Returns a PIL Image given by its input path.
    """
    img =  Image.open(inp)
    return img

def convert(img:Image.Image) -> np.ndarray:
    """
        Converts a PIL image [0,255] to a numpy array [0,1].
    """
    ### STUDENT CODE
    
    out = np.array(img) / 255.0

    ### END STUDENT CODE
    return out

def switch_channels(img:np.ndarray) -> np.ndarray:
    """
        Swaps the red and green channel of a RGB image given by a numpy array.
    """
    ### STUDENT CODE
    
    out = img[:, :, [1, 0, 2]]

    ### END STUDENT CODE

    return out

def image_mark_green(img:np.ndarray) -> np.ndarray:
    """
        returns a numpy-array (HxW) with 1 where the green channel of the input image is greater or equal than 0.7, otherwise zero.
    """
    ### STUDENT CODE
    
    mask = img[:, :, 1] >= 0.7
    
    mask = mask.astype(int)

    ### END STUDENT CODE

    return mask


def image_masked(img:np.ndarray, mask:np.ndarray) -> np.ndarray:
    """
        sets the pixels of the input image to zero where the mask is 1.
    """
    ### STUDENT CODE
    
    out = img.copy()
    out[mask == 1] = 0

    ### END STUDENT CODE

    return out

def grayscale(img:np.ndarray) -> np.ndarray:
    """
        Returns a grayscale image of the input. Use utils.rgb2gray().
    """
    ### STUDENT CODE

    out = utils.rgb2gray(img)
    
    ### END STUDENT CODE

    return out

def cut_and_reshape(img_gray:np.ndarray) -> np.ndarray:
    """
        Cuts the image in half (x-dim) and stacks it together in y-dim.
    """
    ### STUDENT CODE
   
    midpoint = img_gray.shape[1] // 2
    
    left_half = img_gray[:, :midpoint]
    right_half = img_gray[:, midpoint:]
    
    out = np.vstack((right_half, left_half))

    ### END STUDENT CODE

    return out

def filter_image(img:np.ndarray) -> np.ndarray:
    """
        filters the image with the gaussian kernel given below. 
    """

    ### STUDENT CODE
    gaussian = utils.gauss_filter(5, 2)
    g_height, g_width = gaussian.shape

    pad_height = g_height // 2
    pad_width = g_width // 2
    

    out = np.zeros(img.shape)

    # Pad the input image
    padded_img = np.pad(img, ((pad_height, pad_height), (pad_width, pad_width), (0, 0)), mode='constant')

    # channels of the image
    for i in range(img.shape[2]):
        # height of the image
        for y in range(img.shape[0]):
            # width of the image
            for x in range(img.shape[1]):
                out[y, x, i] = np.sum(padded_img[y:y+g_height, x:x+g_width, i] * gaussian)
    
    ### END STUDENT CODE

    return out



def horizontal_edges(img:np.ndarray) -> np.ndarray:
    """
        Defines a sobel kernel to extract horizontal edges and convolves the image with it.
    """
    ### STUDENT CODE
    g = np.array([[1, 0, -1], [2, 0, -2], [1, 0, -1]])

    out = scipy.ndimage.correlate(img, g, mode="constant")
    
    ### END STUDENT CODE

    return out
