# Project name
project(proj3)

# C compiler option
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fopenmp -std=c11")

# Build type
set(CMAKE_BUILD_TYPE Release)

# Source files
set(SOURSES prob1.c)

# Binary file
add_executable(prob1 ${SOURSES})

