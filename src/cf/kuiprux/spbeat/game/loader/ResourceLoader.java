package cf.kuiprux.spbeat.game.loader;

import cf.kuiprux.spbeat.game.ResourceManager;
import cf.kuiprux.spbeat.util.AsyncTask;

import java.nio.file.Path;

public class ResourceLoader implements Loader {

    private ResourceManager manager;

    public ResourceLoader(ResourceManager manager){
        this.manager = manager;
    }

    @Override
    public AsyncTask<Void> loadAll(Path path) {
        return new AsyncTask<>(new AsyncTask.AsyncCallable<Void>() {
            @Override
            public Void get() {
                //manager.add("", path.resolve(""));

                Path texturePath = path.resolve("texture");

                manager.add("texture.btn.settings", texturePath.resolve("button").resolve("settings.png"));
                manager.add("texture.btn.previous", texturePath.resolve("button").resolve("previous.png"));
                manager.add("texture.btn.next", texturePath.resolve("button").resolve("next.png"));
                manager.add("texture.btn.play", texturePath.resolve("button").resolve("play.png"));

                return null;
            }
        });
    }
}