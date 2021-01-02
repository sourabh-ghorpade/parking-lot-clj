(ns amazing-parking-lot.input.runner-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.input.runner :refer [run]]))

(defn- test-command-reader [input-commands-by-order]
  (let [times-called (atom 0)]
    (fn []
      (let [command (.get input-commands-by-order @times-called)]
        (reset! times-called (inc @times-called))
        command))))

(defn- test-writer [output-capture-list-atom]
  (fn [output-message]
    (let [appended-list (conj @output-capture-list-atom output-message)]
      (reset! output-capture-list-atom appended-list))))

(deftest run-test
  (testing "it processes and outputs all inputs till input is not the exit command"
    (let [output-capture-list (atom (vec []))
          input-commands ["create_parking_lot 2"
                          "park \"KA-123\" \"White\""
                          "park \"KA-456\" \"Black\""
                          "park \"KA-789\" \"Red\""
                          "leave 1"
                          "park \"KA-789\" \"Red\""
                          "exit"]
          expected-output ["Created parking lot with 2 slots" "Parked in slot number 1" "Parked in slot number 2"
                           "Parking Lot is full" "Un-parked car \"KA-123\" at slot 1" "Parked in slot number 1"]]
      (run (test-command-reader input-commands) (test-writer output-capture-list))
      (is (= expected-output @output-capture-list)))))
