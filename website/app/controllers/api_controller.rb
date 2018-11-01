class ApiController < ApplicationController
    # skip_before_action :verify_authenticity_token, except: [*]
    protect_from_forgery :only => [:test]   
    
    def send_data

        data = params["string"]
        puts "\n\n"
        
        # data = request.body.read.to_s
        # puts data
        # json_raw = data.to_a[2..-1].join
        json = ActiveSupport::JSON.decode(data)
        puts json["payload_fields"]["message"]
        puts "\n\n"

        # puts json["app_id"]

        # message = 
        # location = 
        # created_at = 
        # updated_at = 
        # longitude =  
        # latitude = 

        message_params = {"message"=>json["payload_fields"]["message"]}
        @message = Message.new(message_params)  
        @message.save
    end
end



# "app_id":"thingtest",
# "dev_id":"zender",
# "hardware_serial":"0004A30B001BC0BA",
# "port":1,
# "counter":0,
# "is_retry":true,
# "payload_raw":"SEVMUAA=",
# "payload_fields":{"message":"HELP"},
# "metadata":{"time":"2018-10-31T19:41:55.838464622Z",
#     "frequency":867.9,
#     "modulation":"LORA",
#     "data_rate":"SF7BW125",
#     "coding_rate":"4/5",
#     "gateways":[{"gtw_id":"eui-b827ebfffef35e71",
#     "timestamp":1099458300,
#     "time":"2018-10-31T19:41:55.819154Z",
#     "channel":7,
#     "rssi":-118,
#     "snr":0.5,
#     "rf_chain":0,
#     "latitude":53.20393,
#     "longitude":6.52751,
#     "altitude":8}]},
# "downlink_url":"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo"}"}
