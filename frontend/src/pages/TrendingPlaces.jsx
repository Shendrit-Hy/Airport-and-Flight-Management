import React, { useEffect, useState } from 'react';
import trendingPlaceService from '../api/trendingPlaceService';


const TrendingPlacesList = ({ tenantId }) => {
  const [places, setPlaces] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchPlaces = async () => {
      if (!tenantId) {
        console.warn("No tenantId provided");
        setError("Tenant ID is required.");
        setLoading(false);
        return;
      }

      try {
        const data = await trendingPlaceService.getTrendingPlaces(tenantId);
        console.log("Fetched places:", data);
        setPlaces(data);
      } catch (err) {
        console.error('Error fetching trending places:', err);
        setError('Failed to load trending places.');
      } finally {
        setLoading(false);
      }
    };

    fetchPlaces();
  }, [tenantId]);

  if (loading) return <p>Loading trending places...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div style={{ padding: '20px' }}>
      <h2 style={{ marginBottom: '20px' }}>Trending Places</h2>
      <div
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))',
          gap: '20px',
        }}
      >
        {places.length > 0 ? (
          places.map((place) => (
            <div
              key={place.id}
              style={{
                border: '1px solid #ddd',
                borderRadius: '8px',
                overflow: 'hidden',
                boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
                backgroundColor: '#fff',
              }}
            >
              <img
                src={place.photoUrl}
                alt={place.description}
                style={{ width: '100%', height: '180px', objectFit: 'cover' }}
              />
              <div style={{ padding: '10px' }}>
                <p style={{ margin: 0 }}>{place.description}</p>
              </div>
            </div>
          ))
        ) : (
          <p>No trending places available.</p>
        )}
      </div>
    </div>
  );
};

export default TrendingPlacesList;
