Rails.application.routes.draw do
  resources :messages
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  devise_for :caretaker, :path => '', :path_names => { :sign_in => "login", :sign_out => "logout", :sign_up => "register" }

  root 'welcome#index'
end
