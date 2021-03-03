/*
 * Copyright (c) 2021 - Present. Rochester Institute of Technology
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.rit.se.design.ArCodePlugin.settings;

import com.intellij.openapi.components.*;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ali Shokri (as8308@rit.edu)
 */

@State(
        name = "ArCodePlugin",
        storages = @Storage("ArCodePlugin.xml")
)
public class ArCodePersistentStateComponent implements PersistentStateComponent<PluginState> {

    PluginState pluginState = new PluginState();

    @Override
    public @Nullable PluginState getState() {
        return pluginState;
    }

    @Override
    public void loadState(@NotNull PluginState state) {
        pluginState = state;
    }

    @JvmStatic
    public static ArCodePersistentStateComponent getInstance(){
        return ServiceManager.getService( ArCodePersistentStateComponent.class );
    }


}
