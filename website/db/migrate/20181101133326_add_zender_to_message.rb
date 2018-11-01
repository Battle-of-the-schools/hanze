class AddZenderToMessage < ActiveRecord::Migration[5.2]
  def change
    add_column :messages, :app_id, :string
    add_column :messages, :dev_id, :string
  end
end
