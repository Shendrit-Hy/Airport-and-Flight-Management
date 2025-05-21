import React, { useEffect, useState } from "react";
import "../styles/TrendingPlaces.css";
import { getTrendingPlaces } from "../api/trendingPlaceService";

import { useLanguage } from "../context/LanguageContext"; // ✅ për përkthim

const TrendingPlaces = () => {
  const [places, setPlaces] = useState([]);
  const { t } = useLanguage(); // ✅ përdor kontekstin e gjuhës



  useEffect(() => {
    getTrendingPlaces()
      .then((res) => {
        setPlaces(res.data);
      })
      .catch((err) => {
        console.error("Gabim gjatë marrjes së trending places:", err);
      });
  }, []);

  return (
    <div className="TrendingPlaces__Wrapper">
      <div className="TrendingPlaces__Container">
        <div className="TrendingPlaces__ContentWrapper">

          <div className="TrendingPlaces__Title">
            {t("TRENDING PLACES", "VENDET MË POPULLORE")}
          </div>

          <div className="TrendingPlaces__CardsWrapper">
            {places.map((place, index) => (
              <div className="TrendingPlaces__Card" key={index}>
                <img
                  src={place.imageUrl || "/edyta.jpg"}
                  alt={place.name}
                  className="TrendingPlaces__Image"
                />
                <div className="TrendingPlaces__Info">
                  <div className="TrendingPlaces__CityTitle">{place.name}</div>
                  <div className="TrendingPlaces__Text">{place.description}</div>
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