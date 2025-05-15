export const getTenantIdFromSubdomain = () => {
  const host = window.location.hostname;
  const parts = host.split('.');
  // Example: tenant1.example.com → returns 'tenant1'
  if (parts.length >= 3) {
    return parts[0];
  }
  return 'default'; // Fallback tenant
};