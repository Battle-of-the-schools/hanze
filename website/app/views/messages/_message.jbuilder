json.extract! message, :id, :message, :location, :created_at, :updated_at
json.url message_url(message, format: :json)