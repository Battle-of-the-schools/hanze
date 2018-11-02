class ApiController < ApplicationController
    require "net/http"

    # skip_before_action :verify_authenticity_token, except: [*]
    protect_from_forgery :only => [:test]   
    
    def send_data
        # data = '{"app_id":"thingtest","dev_id":"zender","hardware_serial":"0004A30B001BC0BA","port":1,"counter":0,"is_retry":true,"payload_raw":"SEVMUAA=","payload_fields":{"message":"HELP"},"metadata":{"time":"2018-10-31T19:41:55.838464622Z","frequency":867.9,"modulation":"LORA","data_rate":"SF7BW125","coding_rate":"4/5","gateways":[{"gtw_id":"eui-b827ebfffef35e71","timestamp":1099458300,"time":"2018-10-31T19:41:55.819154Z","channel":7,"rssi":-118,"snr":0.5,"rf_chain":0,"latitude":53.20393,"longitude":6.52751,"altitude":8}]},"downlink_url":"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo"}'

        if params["payload_raw"] != "AA=="
            if Message.where(dev_id: params["dev_id"]).count == 0
                message_params = {
                    "message" => params["payload_fields"]["message"], 
                    "location" => "haiti", 
                    "latitude" => params["metadata"]["gateways"][0]["latitude"], 
                    "longitude" => params["metadata"]["gateways"][0]["longitude"],
                    "downlink_url" => params["downlink_url"],
                    "app_id" => params["app_id"],
                    "dev_id" => params["dev_id"],
                    "level" => params["payload_fields"]["level"],
                    "pain" => params["payload_fields"]["pain"],
                    "bodypart" => params["payload_fields"]["bodypart"],
                    "cause" => params["payload_fields"]["cause"]
                }
                if params["payload_fields"] == "HELP" || params["payload_fields"] == '"HELP"'
                    message_params["message"] = "HELP"
                end

                @message = Message.new(message_params)  
                @message.save

            else
                @mes = Message.find_by(dev_id: params["dev_id"])
                    value = params["payload_fields"]["message"]
                    value.gsub!("'", '"')
                    data = ActiveSupport::JSON.decode(value)

                    unless data["message"].nil?
                        @mes.update(message: data["message"])
                    end
                    unless data["pain"].nil?
                        @mes.update(pain: data["pain"])
                    end
                    unless data["bodypart"].nil?
                        @mes.update(bodypart: data["bodypart"])
                    end
                    unless data["cause"].nil?
                        @mes.update(cause: data["cause"])
                    end

                @mes.save
            end

            request_params = {"dev_id": params["dev_id"], "confirmed": false, "payload_raw": Base64.encode64("Er zijn nog "+Message.count.to_s+" Wachtende voor u")}
            # url = "https://ptsv2.com/t/4vz8n-1541087003/post"
            url = params["downlink_url"]
            
            JSON.load `curl -H 'Content-Type:application/json' -H 'Accept:application/json' -X POST #{url} -d '#{request_params.to_json}'`
        end
    end
end

# Message(
#     # id: integer,
#     message: string,
#     location: string,
#     # created_at: datetime,
#     # updated_at: datetime,
#     longitude: float,
#     latitude: float,
#     downlink_url: string,
#     app_id: string,
#     dev_id: string,
#     level: integer,
#     pain: integer,
#     bodypart: integer,
#     cause: integer)



# {"app_id"=>"thingtest",
#      "dev_id"=>"atenne",
#      "hardware_serial"=>"0004A30B001A7756",
#      "port"=>1,
#      "counter"=>7,
#      "payload_raw"=>"AA==",
#      "payload_fields"=>{"message"=>"\u0000"},
#  "metadata"=>{"time"=>"2018-11-02T05:19:36.661796754Z",
#      "frequency"=>867.7,
#      "modulation"=>"LORA",
#      "data_rate"=>"SF7BW125",
#      "coding_rate"=>"4/5",
#      "gateways"=>[{"gtw_id"=>"eui-b827ebfffef35e71",
#      "timestamp"=>1901224836,
#      "time"=>"2018-11-02T05:19:36.64069Z",
#      "channel"=>6,
#      "rssi"=>-114,
#      "snr"=>2.5,
#      "rf_chain"=>0,
#      "latitude"=>53.20393,
#      "longitude"=>6.52751,
#      "altitude"=>8}]},
#  "downlink_url"=>"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo",
#  "api"=>{"app_id"=>"thingtest", "dev_id"=>"atenne", "hardware_serial"=>"0004A30B001A7756", "port"=>1, "counter"=>7, "payload_raw"=>"AA==", "payload_fields"=>{"message"=>"\u0000"}, "metadata"=>{"time"=>"2018-11-02T05:19:36.661796754Z", "frequency"=>867.7, "modulation"=>"LORA", "data_rate"=>"SF7BW125", "coding_rate"=>"4/5", "gateways"=>[{"gtw_id"=>"eui-b827ebfffef35e71", "timestamp"=>1901224836, "time"=>"2018-11-02T05:19:36.64069Z", "channel"=>6, "rssi"=>-114, "snr"=>2.5, "rf_chain"=>0, "latitude"=>53.20393, "longitude"=>6.52751, "altitude"=>8}]}, "downlink_url"=>"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo"}}




# {"app_id":"thingtest","dev_id":"zender","hardware_serial":"0004A30B001BC0BA","port":1,"counter":0,"is_retry":true,"payload_raw":"SEVMUAA=","payload_fields":{"message":"HELP"},"metadata":{"time":"2018-10-31T19:41:55.838464622Z","frequency":867.9,"modulation":"LORA","data_rate":"SF7BW125","coding_rate":"4/5","gateways":[{"gtw_id":"eui-b827ebfffef35e71","timestamp":1099458300,"time":"2018-10-31T19:41:55.819154Z","channel":7,"rssi":-118,"snr":0.5,"rf_chain":0,"latitude":53.20393,"longitude":6.52751,"altitude":8}]},"downlink_url":"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo"}
# {"app_id"=>"thingtest", "dev_id"=>"zender", "hardware_serial"=>"0004A30B001BC0BA", "port"=>1, "counter"=>0, "is_retry"=>true, "payload_raw"=>"SEVMUAA=", "payload_fields"=>{"message"=>"HELP"}, "metadata"=>{"time"=>"2018-10-31T19:41:55.838464622Z", "frequency"=>867.9, "modulation"=>"LORA", "data_rate"=>"SF7BW125", "coding_rate"=>"4/5", "gateways"=>[{"gtw_id"=>"eui-b827ebfffef35e71", "timestamp"=>1099458300, "time"=>"2018-10-31T19:41:55.819154Z", "channel"=>7, "rssi"=>-118, "snr"=>0.5, "rf_chain"=>0, "latitude"=>53.20393, "longitude"=>6.52751, "altitude"=>8}]}, "downlink_url"=>"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo", "controller"=>"api", "action"=>"send_data", "api"=>{"app_id"=>"thingtest", "dev_id"=>"zender", "hardware_serial"=>"0004A30B001BC0BA", "port"=>1, "counter"=>0, "is_retry"=>true, "payload_raw"=>"SEVMUAA=", "payload_fields"=>{"message"=>"HELP"}, "metadata"=>{"time"=>"2018-10-31T19:41:55.838464622Z", "frequency"=>867.9, "modulation"=>"LORA", "data_rate"=>"SF7BW125", "coding_rate"=>"4/5", "gateways"=>[{"gtw_id"=>"eui-b827ebfffef35e71", "timestamp"=>1099458300, "time"=>"2018-10-31T19:41:55.819154Z", "channel"=>7, "rssi"=>-118, "snr"=>0.5, "rf_chain"=>0, "latitude"=>53.20393, "longitude"=>6.52751, "altitude"=>8}]}, "downlink_url"=>"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo"}}
# {"app_id"=>"thingtest",
#  "dev_id"=>"zender",
#  "hardware_serial"=>"0004A30B001BC0BA",
#  "port"=>1,
#  "counter"=>0,
#  "is_retry"=>true,
#  "payload_raw"=>"SEVMUAA=",
#  "payload_fields"=>{"message"=>"HELP"},
#  "metadata"=>{"time"=>"2018-10-31T19:41:55.838464622Z",
    #  "frequency"=>867.9,
    #  "modulation"=>"LORA",
    #  "data_rate"=>"SF7BW125",
    #  "coding_rate"=>"4/5",
    #  "gateways"=>[{"gtw_id"=>"eui-b827ebfffef35e71",
        #  "timestamp"=>1099458300,
        #  "time"=>"2018-10-31T19:41:55.819154Z",
        #  "channel"=>7,
        #  "rssi"=>-118,
        #  "snr"=>0.5,
        #  "rf_chain"=>0,
        #  "latitude"=>53.20393,
        #  "longitude"=>6.52751,
        #  "altitude"=>8}]
# },
#  "downlink_url"=>"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo",
#  "controller"=>"api",
#  "action"=>"send_data",
#  "api"=>{"app_id"=>"thingtest",
#  "dev_id"=>"zender", "hardware_serial"=>"0004A30B001BC0BA", "port"=>1, "counter"=>0, "is_retry"=>true, "payload_raw"=>"SEVMUAA=", "payload_fields"=>{"message"=>"HELP"}, "metadata"=>{"time"=>"2018-10-31T19:41:55.838464622Z", "frequency"=>867.9, "modulation"=>"LORA", "data_rate"=>"SF7BW125", "coding_rate"=>"4/5", "gateways"=>[{"gtw_id"=>"eui-b827ebfffef35e71", "timestamp"=>1099458300, "time"=>"2018-10-31T19:41:55.819154Z", "channel"=>7, "rssi"=>-118, "snr"=>0.5, "rf_chain"=>0, "latitude"=>53.20393, "longitude"=>6.52751, "altitude"=>8}]}, "downlink_url"=>"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo"}}



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

