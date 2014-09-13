To test the plugin confman has to be inline

the parameters are
                <executions>
                    <execution>
                        <goals>
                            <goal>read-project-properties</goal>
                        </goals>
                        <configuration>
                            <server>localhost</server>
                            <app>SALES</app>
                            <env>prd</env>
                            <version>1.0.0</version>
                            <port>8082</port>
                        </configuration>
                    </execution>
                </executions>