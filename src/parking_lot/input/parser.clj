(ns parking-lot.input.parser
  (:require [clojure.string :as str]))

(defn parse [input]
  (let [parsed-input (str/split input #" ")]
    {:command   (first parsed-input)
     :arguments (rest parsed-input)
     :result    ""
     :valid?    true}))
