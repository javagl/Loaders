/*
 * www.javagl.de - Loaders
 *
 * Copyright (c) 2013-2017 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Interface for a loader that may load objects of certain types
 * from an input stream.
 */
public interface Loader
{
    /**
     * Returns an unmodifiable list containing the supported file extensions,
     * including the "." dot. Users of this method should handle the returned
     * extensions case-insensitively, as it is not specified whether they
     * are given in upper- or lower case. 
     * 
     * @return The supported file extensions
     */
    List<String> getFileExtensions();
    
    /**
     * Returns an unmodifiable list that contains one description for each
     * {@link #getFileExtensions() supported file extension}
     * 
     * @return The file descriptions
     */
    List<String> getFileDescriptions();
    
    /**
     * Returns the type of the objects that can be loaded
     * 
     * @return The type of the objects that can be loaded
     */
    Class<?> getDataType();
    
    /**
     * Load an object from the given input stream. The type of the returned
     * object must be assignable to the {@link #getDataType() data type} 
     * that is supported by this loader.
     * 
     * @param name A name for the object
     * @param inputStream The input stream to read from
     * @return The loaded object 
     * @throws IOException If an IO error occurs
     */
    Object load(String name, InputStream inputStream) throws IOException;
}
