from typing import List, Tuple

import numpy as np


matrikelnummer = "12323792"

d = {
    'A': int(matrikelnummer[0]),
    'B': int(matrikelnummer[1]),
    'C': int(matrikelnummer[2]),
    'D': int(matrikelnummer[3]),
    'E': int(matrikelnummer[4]),
    'F': int(matrikelnummer[5]),
    'G': int(matrikelnummer[6]),
    'H': int(matrikelnummer[7]),
}


def define_triangle() -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    ### STUDENT CODE

    P1 = np.array((1+d['C'], -(1+d['A']), -(1+d['E'])))
    P2 = np.array((-(1+d['G']), -(1+d['B']), 1+d['H']))
    P3 = np.array((-(1+d['D']), 1+d['F'], -(1+d['B'])))

    ### END STUDENT CODE

    return P1, P2, P3

P1, P2, P3 = define_triangle()
#print("P1:", P1)
#print("P2:", P2)
#print("P3:", P3)

def define_triangle_vertices(P1:np.ndarray, P2:np.ndarray, P3:np.ndarray) -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    ### STUDENT CODE
    
    P1P2 = P2 - P1
    P2P3 = P3 - P2
    P3P1 = P1 - P3

    ### END STUDENT CODE

    return P1P2, P2P3, P3P1

P1P2, P2P3, P3P1 = define_triangle_vertices(P1, P2, P3)
#print("P1P2:", P1P2)
#print("P2P3:", P2P3)
#print("P3P1:", P3P1)

def compute_lengths(P1P2:np.ndarray, P2P3:np.ndarray, P3P1:np.ndarray) -> List[float]:
    ### STUDENT CODE
    
    x = np.sqrt((P1P2[0]**2 + P1P2[1]**2 + P1P2[2]**2))
    y = np.sqrt((P2P3[0]**2 + P2P3[1]**2 + P2P3[2]**2))
    z = np.sqrt((P3P1[0]**2 + P3P1[1]**2 + P3P1[2]**2))

    norms = [x, y, z]

    ### END STUDENT CODE

    return norms

l = compute_lengths(P1P2, P2P3, P3P1)
#print("my Length:", l)
#print("np Length:", [np.linalg.norm(P1P2), np.linalg.norm(P2P3), np.linalg.norm(P3P1)])

def compute_normal_vector(P1P2:np.ndarray, P2P3:np.ndarray, P3P1:np.ndarray) -> Tuple[np.ndarray, np.ndarray]:
    ### STUDENT CODE
    
    n = np.cross(P2P3 - P1P2, P3P1 - P1P2)
    n_normalized = n / np.linalg.norm(n)

    ### END STUDENT CODE

    return n, n_normalized

n, n_normalized = compute_normal_vector(P1P2, P2P3, P3P1)
#print("n:", n)
#print("n_normalized:", n_normalized)


def compute_triangle_area(n:np.ndarray) -> float:
    ### STUDENT CODE
    
    area = 0.5 * np.linalg.norm(n)

    ### END STUDENT CODE

    return area

area = compute_triangle_area(n)
#print("area:", area)


def compute_angles(P1P2:np.ndarray,P2P3:np.ndarray,P3P1:np.ndarray) -> Tuple[float, float, float]:
    ### STUDENT CODE
    
    # Length of the triangle sides
    a = np.linalg.norm(P1P2)
    b = np.linalg.norm(P2P3)
    c = np.linalg.norm(P3P1)

    # cos of each angle
    cos_alpha = (b**2 + c**2 - a**2) / (2 * b * c)
    cos_beta = (a**2 + c**2 - b**2) / (2 * a * c)
    cos_gamma = (a**2 + b**2 - c**2) / (2 * a * b)

    # angles in radian
    alpha_rad = np.arccos(cos_alpha)
    beta_rad = np.arccos(cos_beta)
    gamma_rad = np.arccos(cos_gamma)

    # from radian to degree
    alpha = np.degrees(alpha_rad)
    beta = np.degrees(beta_rad)
    gamma = np.degrees(gamma_rad)

    ### END STUDENT CODE

    return alpha, beta, gamma

alpha, beta, gamma = compute_angles(P1P2, P2P3, P3P1)
#print("alpha in degrees:", alpha)
#print("beta in degrees:", beta)
#print("gamma in degrees:", gamma)
