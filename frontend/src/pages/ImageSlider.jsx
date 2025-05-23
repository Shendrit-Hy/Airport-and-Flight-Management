import React, { useEffect, useState } from 'react';
import Slider from 'react-slick';
import { useNavigate } from 'react-router-dom';
import '../styles/ImageSlider.css';

const ImageSlider = () => {
  const [images, setImages] = useState([]);
  const navigate = useNavigate();

 useEffect(() => {
  // Temporary placeholder images (e.g., aviation or destinations)
  setImages([
    { id: 1, url: '/SliderImage1.webp' },
    { id: 2, url: '/SliderImage2.jpg' },
    { id: 3, url: '/SliderImage3.jpg' },
    { id: 4, url: '/SliderImage4.jpg' },
    { id: 5, url: '/SliderImage5.jpg' },
    { id: 6, url: '/SliderImage6.webp' },
    { id: 7, url: '/SliderImage7.webp' },

  ]);
}, []);


  const settings = {
    dots: false,
    infinite: true,
    speed: 800,
    slidesToShow: 4,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 1000,
    draggable: true,
    swipeToSlide: true,
    cssEase: 'ease-in-out',
  };

  return (
    <section className="slider-section">
      <Slider {...settings}>
        {images.map((img) => (
          <div
            className="slider-image-wrapper"
            key={img.id}
            onDoubleClick={() => navigate('/trending')}
          >
            <img src={img.url} alt={`Slide ${img.id}`} className="slider-image" />
          </div>
        ))}
      </Slider>
    </section>
  );
};

export default ImageSlider;