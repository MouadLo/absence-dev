import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './absence.reducer';
import { IAbsence } from 'app/shared/model/absence.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAbsenceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AbsenceDetail = (props: IAbsenceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { absenceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="intranetApp.absence.detail.title">Absence</Translate> [<b>{absenceEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idAbsence">
              <Translate contentKey="intranetApp.absence.idAbsence">Id Absence</Translate>
            </span>
          </dt>
          <dd>{absenceEntity.idAbsence}</dd>
          <dt>
            <Translate contentKey="intranetApp.absence.horaire">Horaire</Translate>
          </dt>
          <dd>{absenceEntity.horaire ? absenceEntity.horaire.idHoraire : ''}</dd>
          <dt>
            <Translate contentKey="intranetApp.absence.etudiant">Etudiant</Translate>
          </dt>
          <dd>{absenceEntity.etudiant ? absenceEntity.etudiant.cne : ''}</dd>
        </dl>
        <Button tag={Link} to="/absence" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/absence/${absenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ absence }: IRootState) => ({
  absenceEntity: absence.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AbsenceDetail);
