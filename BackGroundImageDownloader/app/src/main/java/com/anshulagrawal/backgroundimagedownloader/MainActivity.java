package com.anshulagrawal.backgroundimagedownloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] ulrs = {"http://www.nasa.gov/sites/default/files/thumbnails/image/potw1823a.jpg",
                "https://apod.nasa.gov/apod/image/1705/Arp273Main_HubblePestana_3079.jpg",
                "http://www.nasa.gov/sites/default/files/archives_ngc6946.jpg",
                "https://cdn.vox-cdn.com/thumbor/AyGBj1C3KgicgO_BtGtLbINeSew=/0x0:2040x1360/1200x800/filters:focal(866x1034:1192x1360)/cdn.vox-cdn.com/uploads/chorus_image/image/59189679/shutterstock_140731432_sized.0.jpg",
                "https://o.aolcdn.com/images/dims?quality=100&image_uri=https%3A%2F%2Fo.aolcdn.com%2Fimages%2Fdims%3Fcrop%3D5708%252C4134%252C0%252C0%26quality%3D85%26format%3Djpg%26resize%3D1600%252C1159%26image_uri%3Dhttp%253A%252F%252Fo.aolcdn.com%252Fhss%252Fstorage%252Fmidas%252Ff595fd83478ace5bc5ff3b76c84850e9%252F206255799%252Fandromeda-galaxy-picture-id680804035%26client%3Da1acac3e1b3290917d92%26signature%3D1e1621698eb1bb62b9edca5efe2c4ebf5df1980a&client=amp-blogside-v2&signature=ba40cd9429fee76950a96928d458e5236527f85d",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6-JV1QQoXIkyLL6u851FBpsJte-RNvgraAhYv_OTwBp5FLt_3",
                "https://cdn-images-1.medium.com/max/1600/0*vl9iVBmCTB65lcTJ.",
                "https://i.ytimg.com/vi/tsjd7xdgfjA/maxresdefault.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGI0rAIVyU-utH5cEWn_7KIQ4mdBHKZbAdtUAvheeOcouEprB9",
                "https://3c1703fe8d.site.internapcdn.net/newman/gfx/news/hires/2018/5b86704d620d1.jpg"
        };

        ListView listView = findViewById(R.id.listview);

        listView.setAdapter(new CustomAdaptor(this, Arrays.asList(ulrs)));

    }
}
