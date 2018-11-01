class WelcomeController < ApplicationController
  def index
    redirect_to messages_path if caretaker_signed_in? 
  end
end
