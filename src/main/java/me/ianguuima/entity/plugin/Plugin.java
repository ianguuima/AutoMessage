package me.ianguuima.entity.plugin;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Plugin {

    private int interval;
    private SendingType type;

    public Plugin(int interval, SendingType type) {
        this.interval = interval;
        this.type = type;
    }


}
