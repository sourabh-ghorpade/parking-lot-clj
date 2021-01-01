Options for removing view logic (printing output) from the domain.
 1. unformatted data which is based on status code and the command(park/leave).
    This data is sent to a view-model to generate the output.
 2. Domain returns a view-model with the data to generate the output
 3. Domain returns an audit event. The log is used to generate the output.

 Stretch
 There is no parking lot. Only an append only log (compilation of past audit actions).
 which is used to construct a parking-lot and create more logs.
 The construction happens within the parking-lot for purely validation purposes.
 i/p & o/p of the parking-lot is always a log.
 Mind = blown. :P

 Park+Leave return an event with a status code.
 For success status code (200), it returns a state change event.
 For Error code: there is no additional information.

 The parking lot has another method to return a generated state
 This state is used for performing queries like status, find car by colour/slot etc.
 The state is leaked in some sense but

 To get there
 MVP = Move to generating the output based on the audit event
 Step 2 = Start collecting the logs and send them to the parking-lot.
 Step 3 = Move domain to re-construct state based on the event logs and run validations,
 and then return an append-only result.
 Step 4 = create a state method within parking lot which generates the state and returns it. 
  Use the state method to generate the status of the parking-lot. This can be done by a view model selected by the 
  executor domain. It can in future be extended to a factory if there can be more than one way of 
  outputting the result.
 Step 5 = Use state method to perform queries.

Sample return type of park/leave
{:status-code 200
:action      {:name        :remove-car / :park-car
             :slot-number 1
             :car         {:license-number "KA" :colour "White"}}}

Sample return of state fn
{:slots [car_one, car_two, nil, car_four]}