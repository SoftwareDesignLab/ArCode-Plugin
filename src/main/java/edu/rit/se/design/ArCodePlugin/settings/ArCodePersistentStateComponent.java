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
