class AddDownLinkToMessage < ActiveRecord::Migration[5.2]
  def change
    add_column :messages, :downlink_url, :string
  end
end
