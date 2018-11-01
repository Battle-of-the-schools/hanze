class AddLocationToCaretaker < ActiveRecord::Migration[5.2]
  def change
    add_column :caretakers, :latitude, :float
    add_column :caretakers, :longitude, :float
  end
end
