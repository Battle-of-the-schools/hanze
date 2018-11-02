class AddMoreToMessage < ActiveRecord::Migration[5.2]
  def change
    add_column :messages, :level, :integer
    add_column :messages, :pain, :integer
    add_column :messages, :bodypart, :integer
    add_column :messages, :cause, :integer
  end
end
