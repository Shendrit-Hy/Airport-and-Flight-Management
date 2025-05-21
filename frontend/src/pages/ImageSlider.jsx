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
    { id: 1, url: 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&w=800&q=80' },
    { id: 2, url: 'https://images.unsplash.com/photo-1589399578939-5cb2139b47f4?auto=format&fit=crop&w=800&q=80' },
    { id: 3, url: 'https://images.unsplash.com/photo-1582570865187-3d5bdb1fba59?auto=format&fit=crop&w=800&q=80' },
    { id: 4, url: 'https://images.unsplash.com/photo-1570129477492-45c003edd2be?auto=format&fit=crop&w=800&q=80' },
    { id: 5, url: 'https://images.unsplash.com/photo-1578939653414-bd4a1e5c1f37?auto=format&fit=crop&w=800&q=80' },
  ]);
}, []);


  const settings = {
    dots: false,
    infinite: true,
    speed: 800,
    slidesToShow: 4,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2500,
    draggable: true,
    swipeToSlide: true,
    cssEase: 'ease-in-out',
  };

  return (
    <section className="slider-section">
      <h2 className="slider-title">Explore Destinations</h2>
      <Slider {...settings}>
        {images.map((img) => (
          <div
            className="slider-image-wrapper"
            key={img.id}
            onDoubleClick={() => navigate('/flights')}
          >
            <img src={img.url} alt={`Slide ${img.id}`} className="slider-image" />
          </div>
        ))}
      </Slider>
    </section>
  );
};

export default ImageSlider;