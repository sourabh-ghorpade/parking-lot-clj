(ns amazing-parking-lot.functional-tests.functional
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.utils.utils :as util]
            [amazing-parking-lot.input.runner :as runner]))

(deftest functional-test
  (testing "it processes all inputs and generates an output till input is not the exit command"
    (let [output-capture-list (atom (vec []))
          input-commands ["park \"KA-123\" \"White\""
                          "leave 1"
                          "create_parking_lot 3"
                          "park \"KA-123\" \"White\""
                          "park \"KA-456\" \"Black\""
                          "park \"KA-789\" \"Red\""
                          "park \"KA-987\" \"Red\""
                          "leave 1"
                          "park \"KA-987\" \"Red\""
                          "registration_numbers_for_cars_with_colour \"Red\""
                          "slot_numbers_for_cars_with_colour \"Red\""
                          "exit"]
          expected-output ["Parking Lot not created"
                           "Parking Lot not created"
                           "Created parking lot with 3 slots"
                           "Parked in slot number 1"
                           "Parked in slot number 2"
                           "Parked in slot number 3"
                           "Parking Lot is full"
                           "Un-parked car \"KA-123\" at slot 1"
                           "Parked in slot number 1"
                           "\"KA-987\", \"KA-789\""
                           "1, 3"]]
      (runner/run (util/test-command-reader input-commands) (util/test-writer output-capture-list))
      (is (= expected-output @output-capture-list)))))