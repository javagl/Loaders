# Loaders

A set of utility classes to manage loaders.

The `Loader` interface is a generic interface for loading objects from
input streams. The `Loaders` class may be used to discover implementations
of this interface that provide a certain data type.

The `FileLoader` interface is an interface that combines multiple 
`Loader` instances, and picks one of them to load an object from a
file, based on the file extension.

## Adding a `Loader` implementation to a project

The `Loader` implementations are internally discovered using a 
`ServiceLoader`. In order to add a `Loader` implementation to 
a project, the file 

    META-INF\services\de.javagl.loaders.Loader
    
has to be added to the project. This file contains the fully qualified
names of the classes that implement the `Loader` interface. For example:

    de.example.PngAsBufferedImageLoader 
    de.example.JpgAsBufferedImageLoader
     

