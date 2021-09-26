(ns amazing-parking-lot.input.command-argument-option)

(defn message [option]
  (:message option))

(defn command [option]
  (:command option))

(defn arguments [option]
  (:arguments option))

(defn parking-lot [option]
  (:parking-lot option))

(defn create-valid-option
  ([command arguments]
   {:command   command
    :arguments arguments
    :message    ""
    :valid?    true})
  ([option message parking-lot]
   {:command     (command option)
    :arguments   (arguments option)
    :message      message
    :parking-lot parking-lot
    :valid?      true}))

(defn create-invalid-option [message]
  {:message message
   :valid? false})

(defmacro if-valid [option function]
  `(if (:valid? ~option)
     (~function ~option)
     ~option))

(defn create-valid-option-with-parking-lot [option parking-lot]
  {:command     (command option)
   :arguments   (arguments option)
   :message      (message option)
   :valid?      true
   :parking-lot parking-lot})

(defn add-parking-lot [option parking-lot]
  (assoc option :parking-lot parking-lot))


