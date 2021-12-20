package com.fadel.go4lunch.data

import com.google.gson.annotations.SerializedName

/*"html_attributions": [],
"next_page_token": "Aap_uECVBJCY8nZXQb9ssfnK_LfkwdVQmAuGmydxymSNo70jkzXT5--voQSJ1PSddb0cMwZQRB_G7DqZ985bw3EcevtGCBw1qPyPcdKxsa2b1b-JO0IYb5-a7KHk3s89slm9GvUsKDeRV330V8RyQ54hQU5QHro3vPRAmOntBo8LGfOs7zmfqtwylJ8RBQGA91qQHwQSTzaTxC0lkQajBfVtnNZcdmbBbzbZ1Y5nv5CroyhVrYhGVNl3NaT0X-h91bUk5uAgE9kFVppAkga3YBAhAD31Ss-BVfLeTVWBPaib0UDIZ-QtR8pU8jE1lTsBYVzCVavVbYQrmUaaQTK2vrzHsGwcK-K3WQnvIkTVbkYJdo46SsTKXpJfsLuwWwwsG5ceOj3Evg7pQe912_kPaOmtLd6NKtT-7AjXpFTL0-cGJJ8KwiZO0pcwPMIgzzaMSScaOg",
"results":
[
{
    "business_status": "OPERATIONAL",
    "geometry":
    {
        "location": { "lat": -33.8587323, "lng": 151.2100055 },
        "viewport":
        {
            "northeast":
            { "lat": -33.85739817010727, "lng": 151.2112278798927 },
            "southwest":
            { "lat": -33.86009782989272, "lng": 151.2085282201073 },
        },
    },
    "icon": "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/bar-71.png",
    "icon_background_color": "#FF9E67",
    "icon_mask_base_uri": "https://maps.gstatic.com/mapfiles/place_api/icons/v2/bar_pinlet",
    "name": "Cruise Bar",
    "opening_hours": { "open_now": false },
    "photos":
    [
        {
            "height": 575,
            "html_attributions":
            [
                '<a href="https://maps.google.com/maps/contrib/112582655193348962755">A Google User</a>',
            ],
            "photo_reference": "Aap_uEDnz_WTtjeSoT06mpN-Yr6NuNoGVip9P74POPIyhLv0Kvr-GORZFWiSGCAf1UnQN29IElWm3J3_KyFw1zUd7IHkVTDDjcjtMp9vQkupTQt0b-BsKLvC6MotMONrakPg4g0Nb-lzhVp8znaorp3klzcdoZJjgamJXZZn6strL82BOIxG",
            "width": 766,
        },
    ],
    "place_id": "ChIJi6C1MxquEmsR9-c-3O48ykI",
    "plus_code":
    {
        "compound_code": "46R6+G2 The Rocks, New South Wales",
        "global_code": "4RRH46R6+G2",
    },
    "price_level": 2,
    "rating": 4.1,
    "reference": "ChIJi6C1MxquEmsR9-c-3O48ykI",
    "scope": "GOOGLE",
    "types":
    ["bar", "restaurant", "food", "point_of_interest", "establishment"],
    "user_ratings_total": 1184,
    "vicinity": "Level 1, 2 and 3, Overseas Passenger Terminal, Circular Quay W, The Rocks",
}*/
data class NearbyPlacesResponse(
    @SerializedName("location")
    val location: Long,

    @SerializedName("place_id")
    val id: String,
)

