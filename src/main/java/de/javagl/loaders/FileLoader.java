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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A class for loading objects from a file, using one of several predefined
 * {@link Loader} instances. Instances of this class may be created with
 * the {@link FileLoaders#create(Class)} method.
 *
 * @param <T> The type of the loaded object
 */
public final class FileLoader<T>
{
    /**
     * The logger used in this class
     */
    static final Logger logger = 
        Logger.getLogger(FileLoader.class.getName());
    
    /**
     * The known loaders
     */
    private final List<Loader> loaders;

    /**
     * Creates a new file loader backed by the given {@link Loader}s.
     * A copy of the given collection will be stored.
     * 
     * @param loaders The {@link Loader}
     */
    FileLoader(Collection<? extends Loader> loaders)
    {
        this.loaders = new ArrayList<Loader>(loaders);
    }
    
    /**
     * Returns an unmodifiable list containing all file extensions that
     * are supported by this file loader, including the "." dot.
     * 
     * @return The list of file extensions
     */
    public List<String> getFileExtensions()
    {
        Set<String> fileExtensions = new LinkedHashSet<String>();
        for (Loader loader : loaders)
        {
            fileExtensions.addAll(loader.getFileExtensions());
        }
        return Collections.unmodifiableList(
            new ArrayList<String>(fileExtensions));
    }
    
    /**
     * Load the object from the given file. This method will try to find 
     * a {@link Loader} that supports the extension of the given file, 
     * and use it to load the file and return the loaded object.
     * If the given file has no extension, or no loader can be found, 
     * an <code>IOException</code> will be thrown.
     * 
     * @param file The file
     * @return The loaded object
     * @throws IOException If the file could not be loaded
     */
    @SuppressWarnings("unchecked")
    public T load(File file) throws IOException
    {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1)
        {
            throw new IOException("File has no extension: "+file);
        }
        String fileExtension = fileName.substring(dotIndex);
        Loader loader = findLoader(fileExtension);
        if (loader == null)
        {
            throw new IOException(
                "No loader found for extension " + fileExtension + 
                " of file " + file);
        }
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(file);
            return (T) loader.load(fileName, inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    logger.warning(e.getMessage());
                }
            }
        }
    }
    
    
    /**
     * Returns the {@link Loader} that supports the given file extension
     * as reported by {@link Loader#getFileExtensions()} (ignoring the
     * case), or <code>null</code> if no such loader is found
     * 
     * @param fileExtension The file extension, including the "." dot 
     * @return The {@link Loader}
     */
    private Loader findLoader(String fileExtension)
    {
        for (Loader loader : loaders)
        {
            List<String> fileExtensions = loader.getFileExtensions();
            for (String e : fileExtensions)
            {
                if (e.equalsIgnoreCase(fileExtension))
                {
                    return loader;
                }
            }
        }
        return null;
        
    }
    
}
