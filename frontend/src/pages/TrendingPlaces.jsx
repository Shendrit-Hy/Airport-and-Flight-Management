import React from "react";
import "../styles/TrendingPlaces.css";

const TrendingPlaces = () => {
  const places = [
    {
      title: "Santorini",
      text: "Known for its stunning sunsets, white-washed buildings, and blue domes over the Aegean Sea."
    },
    {
      title: "Kyoto",
      text: "A cultural treasure with ancient temples, traditional wooden houses, and cherry blossoms in spring."
    },
    {
      title: "Reykjavík",
      text: "A gateway to Iceland’s natural wonders—geysers, waterfalls, and the Northern Lights."
    },
    {
      title: "Marrakech",
      text: "Famous for its vibrant souks, spices, mosaics, and desert adventures."
    }
  ];

  return (
    <div className="TrendingPlaces__Wrapper">
      <div className="TrendingPlaces__Container">
        <div className="TrendingPlaces__ContentWrapper">
          <div className="TrendingPlaces__Title">TRENDING PLACES</div>
          <div className="TrendingPlaces__CardsWrapper">
            {places.map((place, index) => (
              <div className="TrendingPlaces__Card" key={index}>
                <img
                  src="/edyta.jpg"
                  alt={place.title}
                  className="TrendingPlaces__Image"
                />
                <div className="TrendingPlaces__Info">
                  <div className="TrendingPlaces__CityTitle">{place.title}</div>
                  <div className="TrendingPlaces__Text">{place.text}</div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default TrendingPlaces;