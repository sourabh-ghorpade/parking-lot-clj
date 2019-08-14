(ns parking-lot.command.validator
  (:require [parking-lot.input.command-argument-option :as option-builder]))

(def commands [{:name "create_parking_lot" :argument-count 1}])

(defn validate [command-arguments-option]
  (if-not (:valid? command-arguments-option)
    command-arguments-option
    (let [argument-count (count (:arguments command-arguments-option))
          command-name (:command command-arguments-option)
          input-command {:name command-name :argument-count argument-count}]
      (if (some #(= % input-command) commands)
        command-arguments-option
        (option-builder/create-invalid-option "Invalid Command/Arguments")))))