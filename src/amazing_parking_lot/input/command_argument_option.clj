(ns amazing-parking-lot.input.command-argument-option)

(defn result [option]
  (:result option))

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
    :result    ""
    :valid?    true})
  ([option result parking-lot]
   {:command     (command option)
    :arguments   (arguments option)
    :result      result
    :parking-lot parking-lot
    :valid?      true}))

(defn create-invalid-option [result]
  {:result result
   :valid? false})

(defmacro if-valid [option function]
  `(if (:valid? ~option)
     (~function ~option)
     ~option))

(defn create-valid-option-with-parking-lot [option parking-lot]
  {:command     (command option)
   :arguments   (arguments option)
   :result      (result option)
   :valid?      true
   :parking-lot parking-lot})


