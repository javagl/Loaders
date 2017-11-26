/**
 * Utility classes that manage classes that can load data from files and 
 * streams 
 * <p>
 * The {@link de.javagl.loaders.Loaders} class may be used to find
 * implementations of the {@link de.javagl.loaders.Loader} interface
 * for a certain data type, internally locating implementations of
 * this interface using a Java <code>ServiceLoader</code>.<br>
 * <br>
 * The {@link de.javagl.loaders.FileLoaders} class can be used to
 * create {@link de.javagl.loaders.FileLoader} instances that 
 * internally store all {@link de.javagl.loaders.Loader} instances
 * that provide a certain data type, and dispatch the call to load
 * a certain file to a loader that supports the file extension of
 * the respective file. 
 */
package de.javagl.loaders;