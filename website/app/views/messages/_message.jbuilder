json.extract! message, :id, :message, :longitude, :latitude, :created_at, :updated_at
json.url message_url(message, format: :json)