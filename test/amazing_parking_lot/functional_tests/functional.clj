(ns amazing-parking-lot.functional-tests.functional
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.utils.utils :as util]
            [amazing-parking-lot.input.runner :as runner]))

(deftest functional-test
  (testing "it processes all inputs and generates an output till input is not the exit command"
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
      (runner/run (util/test-command-reader input-commands) (util/test-writer output-capture-list))
      (is (= expected-output @output-capture-list)))))