class Message < ApplicationRecord
    reverse_geocoded_by :latitude, :longitude
    
    private

        def place_params
            params.require(:place)
            # .permit(:title, :raw_address, :latitude, :longitude, :visited_by)   TODO: security
        end
end
