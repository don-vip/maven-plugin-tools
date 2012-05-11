package org.apache.maven.plugin.plugin;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.tools.plugin.generator.Generator;
import org.apache.maven.tools.plugin.generator.PluginHelpGenerator;
import org.codehaus.plexus.velocity.VelocityComponent;

import java.io.File;

/**
 * Generates a <code>HelpMojo</code> class.
 *
 * @author <a href="mailto:vincent.siveton@gmail.com">Vincent Siveton</a>
 * @version $Id$
 * @goal helpmojo
 * @phase generate-sources
 * @since 2.4
 */
public class HelpGeneratorMojo
    extends AbstractGeneratorMojo
{
    /**
     * The directory where the generated <code>HelpMojo</code> file will be put.
     *
     * @parameter default-value="${project.build.directory}/generated-sources/plugin"
     */
    protected File outputDirectory;

    /**
     * The name of the package for the generated <code>HelpMojo</code>. By default, the package will be calculated based
     * on the packages of the other plugin goals.
     *
     * @parameter
     * @since 2.6
     */
    private String helpPackageName;

    /**
     * Generate Java 5 sources.
     *
     * @parameter expression="${useJava5}" default-value="false"
     * @since 2.7
     */
    private boolean useJava5;

    /**
     * Velocity component.
     *
     * @component
     * @readonly
     * @required
     */
    private VelocityComponent velocity;

    /**
     * {@inheritDoc}
     */
    protected File getOutputDirectory()
    {
        return outputDirectory;
    }

    /**
     * {@inheritDoc}
     */
    protected Generator createGenerator()
    {
        return new PluginHelpGenerator().setHelpPackageName( helpPackageName ).setUseJava5(
            useJava5 ).setVelocityComponent( this.velocity );
    }

    /**
     * {@inheritDoc}
     */
    public void execute()
        throws MojoExecutionException
    {
        super.execute();

        if ( !project.getCompileSourceRoots().contains( outputDirectory.getAbsolutePath() ) && !skip )
        {
            project.addCompileSourceRoot( outputDirectory.getAbsolutePath() );
        }

    }

}
