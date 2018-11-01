class AddLocationToMessage < ActiveRecord::Migration[5.2]
  def change
    add_column :messages, :longitude, :float
    add_column :messages, :latitude, :float
  end
end
