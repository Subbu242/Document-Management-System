import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080', // Update this if your Spring Boot backend runs on another port
  headers: {
    'Authorization': 'Bearer sampletoken123', // Replace or update this dynamically if needed
  },
});

export const uploadDocument = (formData) => {
  return API.post('/api/documents/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
};

export default API;
