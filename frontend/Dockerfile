# Stage 1: Build React app
FROM node:20-alpine AS build

WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy all source files and build
COPY . .
RUN npm run build

# Stage 2: Serve with NGINX
FROM nginx:alpine

# Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

# Copy React build files to NGINX
COPY --from=build /app/build /usr/share/nginx/html

# Copy custom NGINX config if you have one
 COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy SSL certificates
COPY ssl/ /etc/nginx/ssl/


# Expose port 80
EXPOSE 80 443

# Start NGINX
CMD ["nginx", "-g", "daemon off;"]

