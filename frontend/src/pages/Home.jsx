r: 'white' }}>{t('Calculate Parking Price', 'Llogarit Çmimin e Parkingut')}</h2>
          <Formik
            initialValues={{ entryHour: '', entryMinute: '', exitHour: '', exitMinute: '' }}
            onSubmit={handlePriceCheck}
          >
            {() => (
              <Form className='search-form'>
                <Field type="number" name="entryHour" placeholder={t('Entry Hour (0-23)', 'Ora e Hyrjes (0-23)')} />
                <Field type="number" name="entryMinute" placeholder={t('Entry Minute', 'Minuta e Hyrjes')} />
                <Field type="number" name="exitHour" placeholder={t('Exit Hour (0-23)', 'Ora e Daljes (0-23)')} />
                <Field type="number" name="exitMinute" placeholder={t('Exit Minute', 'Minuta e Daljes')} />
                <button type="submit">{t('Calculate', 'Llogarit')}</button>
              </Form>
            )}
          </Formik>
          {price !== null && (
            <div style={{ color: 'white', marginTop: '1rem' }}>
              {t('Total price:', 'Çmimi total:')} <strong>{price}€</strong>
            </div>
          )}
        </div>
        <div className="parking-price-right-section"></div>
      </section>

      {/* Section 5 */}
      <section className="section fullscreen-image-section">
        <img src="/public/epesta.jpg" alt="Banner" className="full-img" />

      <section className="section weather-section">
        <h2 className="weather-title">{t('5-Day Forecast', 'Parashikimi 5-Ditor')}</h2>
        <div className="weather-cards">
          {forecast.map((day, index) => (
            <div className="weather-card" key={index}>
              <p><strong>{new Date(day.dt_txt).toLocaleDateString()}</strong></p>
              <img src={`https://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png`} alt="weather" />
              <p>{Math.round(day.main.temp)}°C</p>
              <p>{day.weather[0].main}</p>
            </div>
          ))}
        </div>

      </section>
    </div>
  );
};

export default HomePage;