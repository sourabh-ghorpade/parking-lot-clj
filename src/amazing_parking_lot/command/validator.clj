(ns amazing-parking-lot.command.validator
  (:require [amazing-parking-lot.input.command-argument-option :as option-builder]))

(def commands [{:name "create_parking_lot" :argument-count 1}
               {:name "park" :argument-count 2}
               {:name "leave" :argument-count 1}])

(defn- argument-count-is-correct [input-command]
  (some #(= % input-command) commands))

(defn validate-parking-lot-existence [command-arguments-option]
  (if (= (option-builder/command command-arguments-option) "create_parking_lot")
    true
    (not (nil? (option-builder/parking-lot command-arguments-option)))))

(defn validate [command-arguments-option]
  (let [input-command {:name           (:command command-arguments-option)
                       :argument-count (count (:arguments command-arguments-option))}]
    (if (and (argument-count-is-correct input-command))
      (if (validate-parking-lot-existence command-arguments-option)
        command-arguments-option
        (option-builder/create-invalid-option "Parking Lot not created"))
      (option-builder/create-invalid-option "Invalid Command/Arguments"))))