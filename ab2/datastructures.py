from typing import Tuple
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


def define_structures() -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    """
        Defines the two vectors v1 and v2 as well as the matrix M determined by
        your matriculation number.
    """
    ### STUDENT CODE

    v1 = np.array([d['D'], d['A'], d['C']], dtype=np.int_)
    v2 = np.array([d['F'], d['B'], d['E']], dtype=np.int_)

    M = np.array([
            [d['D'], d['B'], d['C']], 
            [d['B'], d['G'], d['A']], 
            [d['E'], d['H'], d['F']]
        ], dtype=np.int_)
    
    ### END STUDENT CODE

    return v1, v2, M

v1, v2, M = define_structures()

print("v1:", v1)
print("v2:", v2)
#print("M:\n", M)
#print("M2:\n", M.transpose())

def sequence(M : np.ndarray) -> np.ndarray:
    """
        Defines a vector given by the minimum and maximum digit of your 
        matriculation number. Step size = 0.25.
    """
    ### STUDENT CODE

    min = M.min()
    max = M.max()
    increment = 0.25

    r = np.arange(min, max + increment, increment)

    ### END STUDENT CODE

    return r

#print("M.min -> M.max:\n", sequence(M))

def matrix(M : np.ndarray) -> np.ndarray:
    """
        Defines the 15x9 block matrix as described in the task description.
    """
    ### STUDENT CODE

    white = np.zeros((3, 3))

    odd = np.hstack((M, white, M))
    even = np.hstack((white, M, white))

    r = np.vstack((odd, even, odd, even, odd))

    ### END STUDENT CODE

    return r

#print("M(15,9):\n", matrix(M))

def dot_product(v1:np.ndarray, v2:np.ndarray) -> float:
    """
        Dot product of v1 and v2.
    """
    ### STUDENT CODE

    r = 0

    for i in range(len(v1)):
        r += v1[i] * v2[i]

    ### END STUDENT CODE

    return r

#print("my v*v:\n", dot_product(v1, v2))
#print("np v*v:\n", np.dot(v1, v2))

def cross_product(v1:np.ndarray, v2:np.ndarray) -> np.ndarray:
    """
        Cross product of v1 and v2.
    """
    ### STUDENT CODE
    x = v1[1] * v2[2] - v1[2] * v2[1]
    y = - v1[0] * v2[2] + v1[2] * v2[0] 
    z = v1[0] * v2[1] - v1[1] * v2[0]

    r = np.array((x, y, z))
    ### END STUDENT CODE

    return r

#print("my vxv:\n", cross_product(v1, v2))
#print("np vxv:\n", np.cross(v1, v2))

def vector_X_matrix(v:np.ndarray, M:np.ndarray) -> np.ndarray:
    """
        Defines the vector-matrix multiplication v*M.
    """
    ### STUDENT CODE
    arr = []

    for i in range(M.shape [1]):
        c = M[:, i]
        res = v[0] * c[0] + v[1] * c[1] + v[2] * c[2]
        arr.append(res)

    r = np.array(arr)
    ### END STUDENT CODE

    return r

#print("my v*M:\n", vector_X_matrix(v1, M))
#print("np v*M:\n", np.dot(v1, M))

def matrix_X_vector(M:np.ndarray, v:np.ndarray) -> np.ndarray:
    """
        Defines the matrix-vector multiplication M*v.
    """
    ### STUDENT CODE
    arr = []

    for i in M:
        res = v[0] * i[0] + v[1] * i[1] + v[2] * i[2]
        arr.append(res)

    r = np.array(arr)
    ### END STUDENT CODE

    return r

#print("my M*v:\n", matrix_X_vector(M, v2))
#print("np M*v:\n", np.dot(M, v2))

def matrix_X_matrix(M1:np.ndarray, M2:np.ndarray) -> np.ndarray:
    """
        Defines the matrix multiplication M1*M2.
    """
    ### STUDENT CODE
    arr = []
    
    for i in M1:
        row = []
        for j in range(M2.shape[0]):
            c = M2[:, j]
            item = 0
            for k in range(len(c)):
                item += i[k]*c[k]
            row.append(item)
        arr.append(row)

    r = np.array(arr)
    ### END STUDENT CODE

    return r

#print("my MxM:\n", matrix_X_matrix(M, M.transpose()))
#print("np MxM:\n", np.matmul(M, M.transpose()))

def matrix_Xc_matrix(M1:np.ndarray, M2:np.ndarray) -> np.ndarray:
    """
        Defines the element-wise matrix multiplication M1*M2 (Hadamard Product).
    """
    ### STUDENT CODE
    arr = []
    
    for i in range(len(M1)):
        row = []
        for j in range(len(M1[0])):
            row.append(M1[i][j] * M2[i][j])
            #print(str(i) + " - " + str(j))
        arr.append(row)

    r = np.array(arr)
    ### END STUDENT CODE
    

    return r

#print("my M*M:\n", matrix_Xc_matrix(M, M.transpose()))
#print("np M*M:\n", np.multiply(M, M.transpose()))