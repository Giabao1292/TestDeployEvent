# Event Management Frontend

Frontend application for event management system built with React + Vite.

## Features

- Event browsing and search
- User authentication and authorization
- Event booking and payment
- Wishlist management
- Review system
- Admin dashboard
- Organizer management

## Development

```bash
# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

## Deployment

The application is configured for deployment on Render.com with the following settings:

- **Build Command**: `npm install && npm run build`
- **Publish Directory**: `dist`
- **Environment**: Static Site

### Environment Variables

Set the following environment variable in your deployment platform:

- `VITE_API_BASE_URL`: Backend API URL (default: https://testdeployevent.onrender.com/api)

## API Configuration

The application is configured to connect to the backend API at:

- Development: http://localhost:8080/api
- Production: https://testdeployevent.onrender.com/api
