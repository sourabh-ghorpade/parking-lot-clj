(ns amazing-parking-lot.input.runner-test
  (:require [clojure.test :refer :all])
  (:require [amazing-parking-lot.input.runner :refer [run]]))

(defn- generate-command [step]
  (case step
    0 "create_parking_lot 2"
    1 "park \"KA-123\" \"White\""
    2 "park \"KA-456\" \"Black\""
    3 "park \"KA-789\" \"Red\""
    4 "leave 1"
    5 "exit"))

(defn- test-command-reader []
  (let [times-called (atom 0)]
    (fn []
      (let [command (generate-command @times-called)]
        (reset! times-called (inc @times-called))
        command))))

(defn- test-writer [output-capture-list-atom]
  (fn [output-message]
    (let [appended-list (conj @output-capture-list-atom output-message)]
      (reset! output-capture-list-atom appended-list))))

(deftest run-test
  (testing "it processes and outputs all inputs till input is not the exit command"
    (let [output-capture-list (atom (vec []))
          expected-output ["Created parking lot with 2 slots"
                           "Parked in slot number 1"
                           "Parked in slot number 2"
                           "Parking Lot is full"
                           "Un-Parked Car \"KA-123\" at slot 1"]]
      (run (test-command-reader) (test-writer output-capture-list))
      (is (= expected-output @output-capture-list)))))
