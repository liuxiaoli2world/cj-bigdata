import * as React from 'react';
import { Router,hashHistory } from 'react-router';
import { resizeEvent} from 'utils/carousel-helper';

import routes from './routes';

const unlisten = hashHistory.listen(location => {
  window.dispatchEvent(resizeEvent);
});

export default (
	
    <Router history={hashHistory} routes={routes}/>
);

