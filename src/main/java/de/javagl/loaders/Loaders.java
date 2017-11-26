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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Logger;

/**
 * Utility methods to obtain {@link Loader} instances for certain data types
 */
public class Loaders
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(Loaders.class.getName());
    
    /**
     * Find all {@link Loader} instances via a <code>ServiceLoader</code> call 
     * that support the given data type, as reported by their 
     * {@link Loader#getDataType()} method
     * 
     * @param dataType The requested data type
     * @return The loader instances
     */
    public static List<Loader> findLoaders(Class<?> dataType) 
    {
        List<Loader> allLoaders = findLoaders();
        List<Loader> matchingLoaders = new ArrayList<Loader>();
        for (Loader loader : allLoaders)
        {
            if (dataType.isAssignableFrom(loader.getDataType()))
            {
                matchingLoaders.add(loader);
            }
        }
        return matchingLoaders;
    }

    /**
     * Find all known implementations of the {@link Loader} interface via 
     * a <code>ServiceLoader</code>
     *  
     * @return The unmodifiable list of all loaders
     */
    private static List<Loader> findLoaders()
    {
        return findImplementations(Loader.class);
    }
    
    /**
     * Find all known implementations of the given interface via 
     * a <code>ServiceLoader</code>
     * 
     * @param <T> The type of the loaded objects
     * 
     * @param i The interface 
     * @return The unmodifiable list of all objects
     */
    private static <T> List<T> findImplementations(Class<T> i) 
    {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(i);
        List<T> implementations = new ArrayList<T>();
        Iterator<T> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) 
        {
            try
            {
                T implementation = iterator.next();
                logger.config(
                    "Found implementation of " + i + ": " + implementation);
                implementations.add(implementation);
            }
            catch (ServiceConfigurationError e)
            {
                logger.warning(e.getMessage());
            }
        }
        return Collections.unmodifiableList(implementations);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Loaders()
    {
        // Private constructor to prevent instantiation
    }

}
