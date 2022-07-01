package de.maxhenkel.audioplayer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.server.MinecraftServer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class Filebin {

    public static void downloadSound(MinecraftServer server, String url, UUID sound) throws IOException, InterruptedException, UnsupportedAudioFileException {
        //String url = getBin(sound);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<InputStream> response = client
                .send(request, responseInfo ->
                        HttpResponse.BodySubscribers.ofInputStream());


        if (response.statusCode() != 200) {
            throw new IOException(url + " responded with status " + response.statusCode());
        }

        AudioManager.saveSound(server, sound, url);
    }
}
