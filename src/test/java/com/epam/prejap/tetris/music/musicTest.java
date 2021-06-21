package com.epam.prejap.tetris.music;


import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertTrue;

@Test
public class musicTest {
    public static final String MUSIC_PATH = "src/main/resources/tetris8bit.wav";
    public void musicFileShouldBeStoredInTheResourcesFolder(){
        File file = new File(MUSIC_PATH);
        assertTrue(file.exists(),"Music file is missing");
    }
}
