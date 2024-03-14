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
        ])
    
    ### END STUDENT CODE

    return v1, v2, M

print(define_structures())

def sequence(M : np.ndarray) -> np.ndarray:
    """
        Defines a vector given by the minimum and maximum digit of your 
        matriculation number. Step size = 0.25.
    """
    ### STUDENT CODE
    # TODO: Implement this function.

	# NOTE: The following lines can be removed. They prevent the framework
    #       from crashing.

    result = np.zeros(10)

    ### END STUDENT CODE


    return result

def matrix(M : np.ndarray) -> np.ndarray:
    """
        Defines the 15x9 block matrix as described in the task description.
    """
    ### STUDENT CODE
    # TODO: Implement this function.

	# NOTE: The following lines can be removed. They prevent the framework
    #       from crashing.

    r = np.zeros((15,9))

    ### END STUDENT CODE

    return r


def dot_product(v1:np.ndarray, v2:np.ndarray) -> float:
    """
        Dot product of v1 and v2.
    """
    ### STUDENT CODE
    # TODO: Implement this function.

	# NOTE: The following lines can be removed. They prevent the framework
    #       from crashing.

    r = np.zeros(v1.shape)

    ### END STUDENT CODE

    return r

def cross_product(v1:np.ndarray, v2:np.ndarray) -> np.ndarray:
    """
        Cross product of v1 and v2.
    """
    ### STUDENT CODE
    # TODO: Implement this function.

	# NOTE: The following lines can be removed. They prevent the framework
    #       from crashing.

    r = np.zeros(v1.shape)
    ### END STUDENT CODE

    return r

def vector_X_matrix(v:np.ndarray, M:np.ndarray) -> np.ndarray:
    """
        Defines the vector-matrix multiplication v*M.
    """
    ### STUDENT CODE
    # TODO: Implement this function.

	# NOTE: The following lines can be removed. They prevent the framework
    #       from crashing.
    r = np.zeros((v.shape[0], M.shape[1]))
    ### END STUDENT CODE

    return r

def matrix_X_vector(M:np.ndarray, v:np.ndarray) -> np.ndarray:
    """
        Defines the matrix-vector multiplication M*v.
    """
    ### STUDENT CODE
    # TODO: Implement this function.

	# NOTE: The following lines can be removed. They prevent the framework
    #       from crashing.
    r = np.zeros((M.shape[0], v.shape[0]))
    ### END STUDENT CODE

    return r

def matrix_X_matrix(M1:np.ndarray, M2:np.ndarray) -> np.ndarray:
    """
        Defines the matrix multiplication M1*M2.
    """
    ### STUDENT CODE
    # TODO: Implement this function.

	# NOTE: The following lines can be removed. They prevent the framework
    #       from crashing.
    r = np.zeros((M1.shape[0], M2.shape[1]))
    ### END STUDENT CODE

    return r

def matrix_Xc_matrix(M1:np.ndarray, M2:np.ndarray) -> np.ndarray:
    """
        Defines the element-wise matrix multiplication M1*M2 (Hadamard Product).
    """
    ### STUDENT CODE
    # TODO: Implement this function.

	# NOTE: The following lines can be removed. They prevent the framework
    #       from crashing.
    r = np.zeros(M1.shape)
    ### END STUDENT CODE
    

    return r
